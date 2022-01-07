import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FactureAchatService } from '../service/facture-achat.service';

import { FactureAchatComponent } from './facture-achat.component';

describe('FactureAchat Management Component', () => {
  let comp: FactureAchatComponent;
  let fixture: ComponentFixture<FactureAchatComponent>;
  let service: FactureAchatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FactureAchatComponent],
    })
      .overrideTemplate(FactureAchatComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureAchatComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FactureAchatService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.factureAchats?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
