import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CiviliteClientService } from '../service/civilite-client.service';

import { CiviliteClientComponent } from './civilite-client.component';

describe('CiviliteClient Management Component', () => {
  let comp: CiviliteClientComponent;
  let fixture: ComponentFixture<CiviliteClientComponent>;
  let service: CiviliteClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CiviliteClientComponent],
    })
      .overrideTemplate(CiviliteClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CiviliteClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CiviliteClientService);

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
    expect(comp.civiliteClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
