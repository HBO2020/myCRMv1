import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PayementFournisseurService } from '../service/payement-fournisseur.service';

import { PayementFournisseurComponent } from './payement-fournisseur.component';

describe('PayementFournisseur Management Component', () => {
  let comp: PayementFournisseurComponent;
  let fixture: ComponentFixture<PayementFournisseurComponent>;
  let service: PayementFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PayementFournisseurComponent],
    })
      .overrideTemplate(PayementFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayementFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PayementFournisseurService);

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
    expect(comp.payementFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
