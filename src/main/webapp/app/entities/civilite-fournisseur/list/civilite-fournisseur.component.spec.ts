import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CiviliteFournisseurService } from '../service/civilite-fournisseur.service';

import { CiviliteFournisseurComponent } from './civilite-fournisseur.component';

describe('CiviliteFournisseur Management Component', () => {
  let comp: CiviliteFournisseurComponent;
  let fixture: ComponentFixture<CiviliteFournisseurComponent>;
  let service: CiviliteFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CiviliteFournisseurComponent],
    })
      .overrideTemplate(CiviliteFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CiviliteFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CiviliteFournisseurService);

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
    expect(comp.civiliteFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
